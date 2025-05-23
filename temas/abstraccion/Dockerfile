# Utiliza la imagen base de SDK para compilación
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

# Establece el directorio de trabajo
WORKDIR /src

# Copia los archivos del proyecto (cambia 'herencia' por 'abstraccion')
COPY ./src/c#/abstraccion.csproj .
RUN dotnet restore

# Copia el resto de archivos fuente
COPY ./src/c#/ .

# Compila y publica la aplicación
RUN dotnet publish -c Release -o /app/publish

# Fase de ejecución - imagen más ligera
FROM mcr.microsoft.com/dotnet/runtime:8.0 AS runtime
WORKDIR /app

# Copia los archivos publicados desde la fase de compilación
COPY --from=build /app/publish .

# Define el comando de entrada
ENTRYPOINT ["dotnet", "abstraccion.dll"]