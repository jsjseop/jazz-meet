# 첫 번째 빌드 단계
FROM node:18-alpine as web
WORKDIR /web
COPY package.json .
RUN npm install
COPY . .
RUN npm run build
  
  # 두 번째 빌드 단계
FROM nginx
COPY ./nginx.conf /etc/nginx/nginx.conf

  # 이전 빌드 단계에서 빌드한 결과물을 /usr/share/nginx/html로 복사
COPY --from=web /web/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
