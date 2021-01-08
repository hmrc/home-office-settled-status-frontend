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

import scala.concurrent.Future

import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.Results.ServiceUnavailable
import play.api.mvc._
import uk.gov.hmrc.homeofficesettledstatus.views.html.{govuk_wrapper, error_template => ShutteringPage}

@Singleton
class ShutteringFilter @Inject()(
  configuration: Configuration,
  val messagesApi: MessagesApi,
  govUkWrapper: govuk_wrapper)(implicit val mat: Materializer)
    extends Filter with I18nSupport {

  private implicit val C: Configuration = configuration

  private lazy val isServiceShuttered: Boolean =
    configuration.getOptional[Boolean]("isShuttered").getOrElse(false)

  private def notARequestForAnAsset(implicit rh: RequestHeader) =
    !(rh.path.startsWith("/template/") || rh.path.startsWith("/check-settled-status/assets/"))

  override def apply(next: RequestHeader => Future[Result])(rh: RequestHeader): Future[Result] = {
    implicit val R: RequestHeader = rh
    if (isServiceShuttered && notARequestForAnAsset)
      Future.successful(
        ServiceUnavailable(
          new ShutteringPage(govUkWrapper)(
            Messages("shuttering.title"),
            Messages("shuttering.heading"),
            Messages("global.error.500.message")
          )
        ))
    else next(rh)
  }
}
