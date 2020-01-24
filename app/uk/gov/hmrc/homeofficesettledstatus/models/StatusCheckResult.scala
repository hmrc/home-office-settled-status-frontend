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

package uk.gov.hmrc.homeofficesettledstatus.models

import play.api.libs.json.Json

case class StatusCheckResult(
  // Date of birth of person being checked in ISO 8601 format
  dateOfBirth: String,
  // Image of the person being checked
  facialImage: String,
  // Full name of person being checked
  fullName: String,
  // 'Right to public fund' statuses
  statuses: List[ImmigrationStatus]
) {

  def mostRecentStatus: ImmigrationStatus =
    statuses.headOption.getOrElse(
      ImmigrationStatus(immigrationStatus = "NONE", rightToPublicFunds = false))

}

object StatusCheckResult {
  implicit val formats = Json.format[StatusCheckResult]
}