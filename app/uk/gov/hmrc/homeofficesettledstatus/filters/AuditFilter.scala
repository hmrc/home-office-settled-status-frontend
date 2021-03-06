/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.homeofficesettledstatus.filters

import akka.stream.Materializer
import play.api.Configuration

import javax.inject.{Inject, Singleton}
import play.api.mvc.RequestHeader
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.audit.http.connector.AuditConnector
import uk.gov.hmrc.play.audit.model.DataEvent
import uk.gov.hmrc.play.bootstrap.config.{ControllerConfigs, HttpAuditEvent}
import uk.gov.hmrc.play.bootstrap.frontend.filters.DefaultFrontendAuditFilter

import scala.concurrent.ExecutionContext

@Singleton
class AuditFilter @Inject()(
  config: Configuration,
  val controllerConfigs: ControllerConfigs,
  override val auditConnector: AuditConnector,
  val httpAuditEvent: HttpAuditEvent,
  override val mat: Materializer)(implicit override protected val ec: ExecutionContext)
    extends DefaultFrontendAuditFilter(config, controllerConfigs, auditConnector, httpAuditEvent, mat) {

  override val maskedFormFields: Seq[String] = Seq.empty

  override val applicationPort: Option[Int] = None

  override def dataEvent(
    eventType: String,
    transactionName: String,
    request: RequestHeader,
    detail: Map[String, String])(implicit hc: HeaderCarrier): DataEvent =
    httpAuditEvent
      .dataEvent(eventType, transactionName, request, detail.updated("authId", "-"))

  override def controllerNeedsAuditing(controllerName: String): Boolean =
    !controllerName.contains("Assets") && controllerConfigs.controllerNeedsAuditing(controllerName)
}
