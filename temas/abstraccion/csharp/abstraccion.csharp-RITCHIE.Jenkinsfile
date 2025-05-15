pipeline {
    agent any   // Usar cualquier agente disponible

    // Definir las variables de entorno necesarias
    stages {
        stage('Setup .NET') {
            steps {
                sh 'dotnet --version'
            }
        }

        stage('Restaurar') {
            steps {
                // Restaurar el proyecto principal
                dir('Figuras') {
                    sh 'dotnet restore'
                }
        
                // Restaurar el proyecto de tests
                dir('Figuras.Tests') {
                    sh 'dotnet restore'
                }
            }
        }

        stage('Compilar') {
            steps {
                dir('Figuras') {
                    sh 'dotnet build --no-restore'
                }
            }
        }

        stage('Test') {
            steps {
                dir('Figuras.Tests') {
                    // Ejecutar tests con restauración explícita
                    sh 'dotnet test --no-restore --verbosity normal'
                }
            }
        }
    }

    post {
        always {
            sh 'echo "Pipeline completado - Ver resultados en consola"'
        }
    }
}