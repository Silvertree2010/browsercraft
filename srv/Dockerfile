FROM node:24-alpine
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci --omit=dev
COPY server.mjs ./
ENV BC_ROOT=/app/web BC_DATA=/data BC_STATE=/state PORT=8090
EXPOSE 8090
CMD ["node", "server.mjs"]
