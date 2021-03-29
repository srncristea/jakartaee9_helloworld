
FROM node:14.17.0-alpine as build-stage
WORKDIR /app
COPY . .
COPY ./docker/nginx/nginx-custom.conf .
COPY ./docker/ssl/localhost.crt .
COPY ./docker/ssl/localhost.key .
RUN yarn install && yarn build

FROM nginx:1.21.0-alpine

RUN mkdir -p /etc/ssl/certs \
    && mkdir -p /etc/ssl/private
# && cp ./docker/ssl/localhost.crt /etc/ssl/certs \
# && cp ./docker/ssl/localhost.key /etc/ssl/certs

WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=build-stage /app/build .
COPY --from=build-stage /app/nginx-custom.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /app/localhost.crt /etc/ssl/certs
COPY --from=build-stage /app/localhost.key /etc/ssl/private

# RUN mkdir -p /etc/ssl/certs \
#     && cp ./docker/ssl/localhost.crt /etc/ssl/certs \
#     && cp ./docker/ssl/localhost.key /etc/ssl/certs

ENTRYPOINT ["nginx", "-g", "daemon off;"]