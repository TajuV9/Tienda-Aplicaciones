# Deployment guide

## With docker

1. Download the Docker image from GitHub Container Registry (GHCR) or build it yourself.
2. Run the container with the following considerations:
    - You should map the internal port 8080 to whatever port you want.
    - You should set the environment variables.

## Without docker

1. Download the .jar file.
2. Place the .jar file into an empty folder.
3. Set the environment variables acordingly.
4. Run the jar file.

## Environment variables

Please, note that this values are an example, and you should replace them.

```env
APPXTRACT_BASE_URL=http://localhost:8080

APPXTRACT_DB_HOST=localhost
APPXTRACT_DB_PORT=5432
APPXTRACT_DB_NAME=AppStore
APPXTRACT_DB_USER=postgres
APPXTRACT_DB_PASSWORD=password

APPXTRACT_JWT_SECRET=bc52f81bec0d08e9077b94bcc6828eaf7e3ec0801bb56d1286bc3d8372da204cf014d0bf03326fe6a741eae238a9a8e8207ff5fd5ea180e5eb5580c00fd9607214efce19102b45629aba81f07fc5cc376ced22a9dc71410bc647e88470b2f15227a538a47c28b6aab1e73682de8e2603dc8d1b6559645399bff1f671ee64061688a74bde08409172022d8084469e489219a0a423f8f23c09e0ed390cfd185988e5fed94f962ce5852cecd114cc6e17c2b9e3615b59ced70f2cd0af9f32915a7006ebf9fca616e91236d82229288501c38d855180e1161bf6ddbe1f697678d3fcff2e5eb4215458e0bbb0fd493713b0ee1e131c9cec7870294b65e60bcdc94974
```