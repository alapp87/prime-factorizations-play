# Prime Factorization Play

This project will calculate prime factors of a given number. A prime number is a whole number greater than 1 whose only
factors are 1 and itself. Prime factors are prime numbers that can be multiplied together to get the original number.

Example:
Prime factors of 15 are 3 and 5 (3 x 5 = 15, 3 and 5 are prime numbers)

Written in Scala using the Play framework.

### Prerequisites
1. Java SE 1.8 or higher
1. sbt has been installed and configured properly
1. scala has been installed and configured properly

### How To
1. Clone project or download and unzip the project file
1. Open a terminal at the project's root directory
1. Execute `sbt compile` to compile the code
1. Execute `sbt run` to start the application locally
1. Visit **http://localhost:9000**
    1. You may see an error page that says "Database 'default' needs evolution!"
    1. Please click the "Apply this script now!" button to initialize database
1. Once the index page has been loaded, enter a number greater than 1 in the *Number* text input
1. The prime factors equation will be output under the *Equation* label
1. To quit, in the terminal, press `Ctrl+C`

### Testing
1. To run tests, execute the `sbt test` command in the terminal