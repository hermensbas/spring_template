.PHONY: \
help compile \
test-unit test-int test-all \
versions-show versions-update \
docker-login docker-up docker-down docker-data\
build-template build-template-docker run-template kill-template \
build-opera build-opera-docker run-opera kill-opera \
build-afwijk build-afwijk-docker run-afwijk kill-afwijk
CURRENT_DIR = $(realpath .)

#####################################################################################################
########## help
#####################################################################################################
help:
	@echo "Available targets for:"
	@echo "########################################################################################"
	@echo "  - help\t\t\tThis help"
	@echo "  - compile\t\t\tCompiles code"
	@echo "---------------------------------------------------------------[test]-------------------"
	@echo "  - test\t\t\tUnit-tests"
	@echo "  - test-int\t\t\tIntegration-tests"
	@echo "  - test-all\t\t\tUnit and integration tests"
	@echo "---------------------------------------------------------------[maven]------------------"
	@echo "  - versions-show\t\tShows outdated POM versions"
	@echo "  - versions-update\t\tUpdate POM versions"
	@echo "  - deps\t\t\tShows dependency tree"
	@echo "---------------------------------------------------------------[docker-compose]---------"
	@echo "  - docker-login\t\tLogin harbor username/cliSecret"
	@echo "  - docker-up\t\t\tRuns docker"
	@echo "  - docker-down\t\t\tStops docker"
	@echo "  - docker-data\t\t\tRuns docker configures and fills the databases"
	@echo "---------------------------------------------------------------[template]---------------"
	@echo "  - build-template\t\tBuilds template app"
	@echo "  - build-template-docker\tBuilds docker template app"
	@echo "  - run-template\t\tRuns template app"
	@echo "  - kill-template\t\tKills app-template process"
	@echo "---------------------------------------------------------------[opera]------------------"
	@echo "  - build-opera\t\t\tBuilds opera app"
	@echo "  - build-opera-docker\t\tBuilds docker opera app"
	@echo "  - run-opera\t\t\tRuns opera app"
	@echo "  - kill-opera\t\t\tKills app-opera process"
	@echo "---------------------------------------------------------------[afwijk]-----------------"
	@echo "  - build-afwijk\t\tBuilds afwijk app"
	@echo "  - build-afwijk-docker\t\tBuilds docker afwijk app"
	@echo "  - run-afwijk\t\t\tRuns afwijk app"
	@echo "  - kill-afwijk\t\t\tKills app-afwijk process"
	@echo "########################################################################################"


#####################################################################################################
########## compile
#####################################################################################################
compile:
	@echo "########################################################################################"
	@echo "## mvn clean compile"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn clean compile 


#####################################################################################################
########## help
#####################################################################################################
# tests units the code
test:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl test-aggregator -am clean verify -Ptest-unit
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"

# tests integration the code
test-int:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl test-aggregator -am clean verify -Ptest-integration
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"

# tests all the code
test-all:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl test-aggregator -am clean verify -Ptest-all
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"


#####################################################################################################
########## maven
#####################################################################################################
versions-show:
	@echo "########################################################################################"
	@echo "## mvn versions:display-dependency-updates -Dmaven.plugin.validation=VERBOSE"
	@echo "## versions:display-plugin-updates -Dmaven.plugin.validation=VERBOSE"
	@echo "########################################################################################"
	@echo "########################################################################################"
    #@mvn -U versions:update-properties
	@mvn versions:display-dependency-updates
	@mvn versions:display-plugin-updates

versions-update:
	@echo "########################################################################################"
	@echo "@mvn -U versions:update-properties "
	@echo "	@mvn -U versions:dependency-updates "
	@echo "	@mvn -U versions:plugin-updates "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -U versions:update-properties 
	@mvn -U versions:use-latest-versions 
	@mvn -U versions:commit 

deps:
	@echo "########################################################################################"
	@echo "## mvn dependency:tree"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn dependency:tree


#####################################################################################################
########## docker
#####################################################################################################
docker-login:
	@echo "########################################################################################"
	@echo "## docker login harbor-gn2.cicd.s15m.nl"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker login harbor-gn2.cicd.s15m.nl

docker-build: build-template-docker build-opera-docker build-afwijk-docker

docker-up: docker-build
	@echo "########################################################################################"
	@echo "## docker compose -f .docker/docker-compose.yml -p myapp up -d --remove-orphans"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker compose -f .docker/docker-compose.yml -p myapp up -d --remove-orphans

docker-down:
	@echo "########################################################################################"
	@echo "## docker compose -f .docker/docker-compose.yml -p myapp down -v"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker compose -f .docker/docker-compose.yml -p myapp down -v

docker-data:
	@echo "########################################################################################"
	@echo "## docker compose -f .docker/docker-compose.yml -p myapp --profile data-filler up"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker compose -f .docker/docker-compose.yml -p myapp --profile data-filler up


#####################################################################################################
########## app-template
#####################################################################################################
build-template:
	@echo "########################################################################################"
	@echo "## mvn -pl app-template -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-template -am clean -Dskip.unit.tests=true package

build-template-docker: build-template
	@echo "########################################################################################"
	@echo "## docker build -t services/app-template:latest -f app-template/docker/Dockerfile ./app-template"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker build -t services/app-template:latest -f app-template/docker/Dockerfile ./app-template

run-template:
	@echo "########################################################################################"
	@echo "## mvn -pl app-template -am clean spring-boot:run"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-template -am clean spring-boot:run

kill-template:
	@sudo kill -9 $(sudo lsof -t -i:8080) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9080) > /dev/null 2>&1


#####################################################################################################
########## app-opera
#####################################################################################################
build-opera:
	@echo "########################################################################################"
	@echo "## mvn -pl app-opera -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-opera -am clean -Dskip.unit.tests=true package

build-opera-docker: build-opera
	@echo "########################################################################################"
	@echo "## docker build -t services/app-opera:latest -f app-opera/docker/Dockerfile ./app-opera"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker build -t services/app-opera:latest -f app-opera/docker/Dockerfile ./app-opera

run-opera:
	@echo "########################################################################################"
	@echo "## mvn -pl app-opera -am clean spring-boot:run"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-opera -am clean spring-boot:run

kill-opera:
	@sudo kill -9 $(sudo lsof -t -i:8081) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9081) > /dev/null 2>&1


#####################################################################################################
########## app-afwijk
#####################################################################################################
build-afwijk:
	@echo "########################################################################################"
	@echo "## mvn -pl app-afwijk -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-afwijk -am clean -Dskip.unit.tests=true package

build-afwijk-docker: build-afwijk
	@echo "########################################################################################"
	@echo "## docker build -t services/app-afwijk:latest -f app-afwijk/docker/Dockerfile ./app-afwijk"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker build -t services/app-afwijk:latest -f app-afwijk/docker/Dockerfile ./app-afwijk

run-afwijk:
	@echo "########################################################################################"
	@echo "## mvn -pl app-template -am clean spring-boot:run"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn -pl app-afwijk -am clean spring-boot:run

kill-afwijk:
	@sudo kill -9 $(sudo lsof -t -i:8082) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9082) > /dev/null 2>&1
