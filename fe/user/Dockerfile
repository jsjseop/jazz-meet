# 첫 번째 빌드 단계
FROM node:18-alpine as web
WORKDIR /web
COPY package.json .
RUN npm install
COPY . .
RUN npm run build

