[![CircleCI](https://circleci.com/gh/phamed11/phoneBook.svg?style=svg)](https://circleci.com/gh/phamed11/phoneBook)

# Phone book command-line application

Simple command-line application to store phone numbers in Json format.

## Getting Started

### Prerequisites
 * Intellij IDEA
 
#### Set up Lombok
 
 Our project uses [Lombok](https://projectlombok.org/), to enable it in IntelliJ you have to add its plugin 
  * follow these [instructions](https://projectlombok.org/setup/intellij)
  * restart IntelliJ IDEA
  * make sure that annotation processing is enabled in `Settings>Build>Compiler>Annotation processors`
  
#### No data repositories used

  Application creates an Assets folder where is stores data. Optionally can
  be configured to store files in the Home directory.
  (settings: PhonebookServiceImpl -> use UPLOAD_FOLDER)
  
  



