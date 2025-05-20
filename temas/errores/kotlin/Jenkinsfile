pipeline {
    agent {
        docker {
            image 'gradle:jdk17'  // imagen oficial con Gradle y JDK 17
            args '-v $HOME/.gradle:/home/gradle/.gradle'  // para cachear dependencias
        }
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'chmod +x ./gradlew'  // si usas gradlew local
                sh './gradlew clean test' // ejecuta build y pruebas
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/test/*.xml'  // recoge reportes JUnit
        }
    }
}
