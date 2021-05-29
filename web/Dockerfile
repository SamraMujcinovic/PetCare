FROM node:8.16 as build-deps
WORKDIR /web
COPY package.json yarn.lock ./
RUN yarn
COPY . ./
RUN yarn build