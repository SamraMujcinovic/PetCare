FROM node:erbium-alpine3.11
WORKDIR /web
COPY package.json yarn.lock ./
RUN yarn
COPY . .
EXPOSE 3000
RUN yarn start