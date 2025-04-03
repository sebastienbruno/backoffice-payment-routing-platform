# NO build here – only serve built files
FROM nginx:alpine
COPY ./dist/apps/backoffice-payment-frontend/browser /usr/share/nginx/html
COPY ./apps/backoffice-payment-frontend/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
