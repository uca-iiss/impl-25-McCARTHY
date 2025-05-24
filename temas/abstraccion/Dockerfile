# Fase de construcción - usa .NET 9.0 como en el ejemplo oficial
FROM mcr.microsoft.com/dotnet/sdk:9.0@sha256:3fcf6f1e809c0553f9feb222369f58749af314af6f063f389cbd2f913b4ad556 AS build
WORKDIR /src

# Copia solo el archivo del proyecto primero (para optimizar caché)
COPY ./temas/abstraccion/src/c#/abstraccion.csproj .
RUN dotnet restore

# Copia el resto del código fuente
COPY ./temas/abstraccion/src/c#/ .

# Compila y publica la aplicación
RUN dotnet publish -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/runtime:9.0@sha256:d067453026c556115c214e81d4f93adaa190487fad6d7ef4b539eab6f8103d2e
WORKDIR /app
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "abstraccion.dll"]