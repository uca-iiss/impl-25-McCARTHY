FROM node:18

WORKDIR /app

COPY . .

RUN npm install
RUN npm run build
RUN npm run test

CMD ["npm", "run", "start"]
