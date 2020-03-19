/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.homeofficesettledstatus.wiring

import com.typesafe.config.ConfigFactory
import play.api.{Configuration, Mode}
import uk.gov.hmrc.play.bootstrap.config.{RunMode, ServicesConfig}
import uk.gov.hmrc.play.test.UnitSpec

class AppConfigSpec extends UnitSpec {

  val testConfigString: String =
    """
      |run.mode="Dev"
      |appName="Bonkers"
      |microservice.services.auth.port=8500
      |microservice.services.auth.host=localhost
      |microservice.services.home-office-settled-status-proxy.host=localhost
      |microservice.services.home-office-settled-status-proxy.port=9388
      |mongodb.session.expireAfterSeconds=2
      |authorisedStrideGroup="Yes"
    """.stripMargin

  "AppConfigImpl" should {

    val testConfig = Configuration(ConfigFactory.parseString(testConfigString))
    val testRunMode = new RunMode(testConfig, Mode.Dev)
    val testServicesConfig: ServicesConfig = new ServicesConfig(testConfig, testRunMode)
    val testAppConfig = new AppConfigImpl(testServicesConfig)

    "retrieve the name of the app from the service configuration and store it as appName" in {

      testAppConfig.appName shouldBe "Bonkers"
    }

    "have correct auth URL" in {

      testAppConfig.authBaseUrl shouldBe "http://localhost:8500"
    }

    "have correct proxy URL" in {

      testAppConfig.homeOfficeSettledStatusProxyBaseUrl shouldBe "http://localhost:9388"
    }

    "have the correct mongo expiry time" in {

      testAppConfig.mongoSessionExpiryTime shouldBe 2
    }

    "have the correct information if this is an authorised stride user" in {

      testAppConfig.authorisedStrideGroup shouldBe "Yes"
    }

  }
}
