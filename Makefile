tests:
	@ ./mvnw test

package:
	@ ./mvnw clean package -DskipTests

docker-image-build:
	@ $(MAKE) package
	@ docker build -t compassouol/product-ms .

run:
	@ $(MAKE) stop
	@ $(MAKE) docker-image-build
	@ docker-compose up -d

run-database:
	@ $(MAKE) stop
	@ $(MAKE) docker-image-build
	@ docker-compose up -d
	@ docker stop application-product-ms

stop:
	@ docker-compose down -v
