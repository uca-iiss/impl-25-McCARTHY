FROM node:18

WORKDIR /app

# Copia primero los package.json para usar el cache de Docker
COPY package*.json ./
RUN npm install

# Ahora copia el resto del código
COPY . .

# Construir TypeScript
RUN npm run build

# Por defecto, correr tests (puedes cambiarlo según etapa)
CMD ["npm", "run", "test"]
