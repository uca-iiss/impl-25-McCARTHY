

## Implantación

Pon los siguientes comandos dentro de `lambdas/ruby`:

```bash
terraform init
terraform apply
```

Accede a `localhost:8080`. En caso de que te pregunte por la constrseña inicial, poner:
```bash
docker exec -it jenkins_ruby cat /var/jenkins_home/secrets/initialAdminPassword
```

Crear el Pipeline:

    1. En el panel de Jenkins, haz clic en “New Item”.
    2. Introduce un nombre para el proyecto.
    3. Selecciona “Pipeline” como tipo de proyecto y haz clic en OK.
    4. En “Definition” selecciona Pipeline script from SCM
    5. En “SCM” elige Git
    6. En “Repository URL” introduce: `https://github.com/uca-iiss/RITCHIE-impl-25`
    7. En rama poner la que corresponda
    8. En Jenkinsfile, poner temas/lambdas/ruby/Jenkinsfile
    9. Haz clic en Save


`Destruir todo (limpieza)`
Ejecuta:
```bash
terraform destroy
```
Luego, para eliminar todos los archivos generados por Terraform:
```bash
rm -rf .terraform terraform.tfstate terraform.tfstate.backup
```