# My School
Групов проект за електронен дневник

## Изисквания
* Java 17 (тествано с Liberica 17)
* MySQL 8.3

## Първоначална настройка
1. Създайте `.env` като използвате примерното съдържание на `.env.sample`. В новосъздадения файл направете необходимите настройки за връзка до MySQL.
2. Уверете се, че променливите, зададени в `.env`, се зареждат в JRE. При IntelliJ IDEA това се постига като от настройките на билда, файлът се избере в Enviroment Variables полето.
3. При първоначално стартиране, ще бъдат създаден по потребител за роля, чиите потребителски имена и пароли ще бъдат името на ролята, изписана с малки букви. Например, за `Role.ADMIN` ще се създаде потребител `admin` с парола `admin`.
4. По подразбиране, стартираното приложение се достъпва от `localhost:8080` на машината, на която е стартиран. 