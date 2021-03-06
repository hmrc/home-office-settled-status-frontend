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

package uk.gov.hmrc.homeofficesettledstatus.config

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest

class AppConfigSpec extends PlaySpec with GuiceOneAppPerSuite {

  implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

  "Application configuration" when {

    "contains correct configured values" must {

      "host" in {
        appConfig.appName mustBe "home-office-settled-status-frontend"
      }

      "authBaseUrl" in {
        appConfig.authBaseUrl mustBe "http://localhost:8500"
      }

      "homeOfficeSettledStatusProxyBaseUrl" in {
        appConfig.homeOfficeSettledStatusProxyBaseUrl mustBe "http://localhost:9388"
      }

      "mongoSessionExpiryTime" in {
        appConfig.mongoSessionExpiryTime mustBe 3600
      }

      "authorisedStrideGroup" in {
        appConfig.authorisedStrideGroup mustBe "TBC"
      }

      "defaultQueryTimeRangeInMonths" in {
        appConfig.defaultQueryTimeRangeInMonths mustBe 6
      }

      "gtmId" in {
        appConfig.gtmId mustBe "N/A"
      }

    }
  }
}
