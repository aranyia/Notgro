# Notgro

## Overview
Notgro is a retail website where the following discounts apply:

1. If the user is an employee of the store, he gets a 30% discount
2. If the user is an affiliate of the store, he gets a 10% discount
3. If the user has been a customer for over 2 years, he gets a 5% discount.
4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as a discount).
5. The percentage based discounts do not apply on groceries.
6. A user can get only one of the percentage based discounts on a bill.

## Installation

### Prerequisites
To build from source, you need the following installed (and available in your $PATH):

* [Java 8+](http://java.oracle.com)

* [Apache Maven 3 or greater](http://maven.apache.org/)

### Building
After cloning the project, you can build it from source with this command:
```
mvn package
```
And you can install it into your Maven repository:
```
mvn install
```

## Test coverage report
During packaging or installation, Maven runs JUnit tests under code coverage and creates a coverage report with [JaCoCo](www.eclemma.org/jacoco). The generated report can be found in the site folder: _target/site/jacoco/index.html_

## Licence
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.