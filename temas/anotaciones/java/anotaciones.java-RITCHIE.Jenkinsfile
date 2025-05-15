pipeline {
    agent any   // Usar cualquier agente disponible

    // Definir las herramientas necesarias
    tools {
        maven 'Maven' 
        jdk 'JDK17'
    }

    stages {
        stage('Check') {
            steps {
                dir('java') {
                    sh 'mvn dependency:tree'
                }
            }
        }

        stage('Compilar') {
            steps {
                dir('java') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('java') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'java/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                dir('java') {
                    sh 'mvn package -DskipTests'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'java/target/*.jar', fingerprint: true
        }
    }
}