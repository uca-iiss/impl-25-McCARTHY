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
                git branch: 'main', 
                    url: 'https://github.com/AdolfoGomezMorera/IISS.git'
            }
        }

        stage('Compilar') {
            steps {
                dir('temas/anotaciones/java') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('temas/anotaciones/java') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'temas/anotaciones/java/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                dir('temas/anotaciones/java') {
                    sh 'mvn package -DskipTests'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'temas/anotaciones/java/target/*.jar', fingerprint: true
        }
    }
}