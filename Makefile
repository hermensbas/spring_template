.PHONY: help compile test-unit test-int test-all build-template build-template-docker run-template docker-up docker-down versions-show versions-update kill-template

CURRENT_DIR = $(realpath .)

help:
	@echo "Available targets for:"
	@echo "########################################################################################"
	@echo "  - help\t\t\tThis help"
	@echo "  - compile\t\t\tCompiles code"
	@echo "  - test\t\t\tUnit-tests"
	@echo "  - test-int\t\t\tIntegration-tests"
	@echo "  - test-all\t\t\tUnit and integration tests"
	@echo "  - build-template\t\tBuilds template app"
	@echo "  - build-template-docker\tBuilds docker template app"
	@echo "  - run-template\t\tRuns template app"
	@echo "  - docker-up\t\t\tRuns docker"
	@echo "  - docker-down\t\t\tStops docker"
	@echo "  - versions-show\t\tShows outdated POM versions"
	@echo "  - versions-update\t\tUpdate POM versions"
	@echo "  - deps\t\t\tShows dependency tree"
	@echo "  - kill-template\t\t\tKills app-template process"
	@echo "########################################################################################"

# compiles the code
compile:
	@echo "########################################################################################"
	@echo "## mvn clean compile"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn clean compile 

# tests units the code
test:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl test-aggregator -am clean verify -Ptest-unit
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"

# tests integration the code
test-int:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl test-aggregator -am clean verify -Ptest-integration
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"

# tests all the code
test-all:
	@echo "########################################################################################"
	@echo "## mvn -pl test-aggregator -am clean verify "
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl test-aggregator -am clean verify -Ptest-all
	@echo -n "## CODE_COVERAGE_PERCENTAGE: "
	@cat test-aggregator/target/site/jacoco-aggregate/index.html | grep -o "Total[^%]*%" | sed "s/Total<.*>\([0-9]*\)%/\1/"

build-template:
	@echo "########################################################################################"
	@echo "## mvn -pl app-template -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
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
	@clear
	@mvn -pl app-template -am clean spring-boot:run

docker-build: build-template-docker

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


versions-show:
	@echo "########################################################################################"
	@echo "## mvn versions:display-dependency-updates -Dmaven.plugin.validation=VERBOSE"
	@echo "## versions:display-plugin-updates -Dmaven.plugin.validation=VERBOSE"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
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
	@clear
	@mvn -U versions:update-properties 
	@mvn -U versions:use-latest-versions 
	@mvn -U versions:commit 

deps:
	@echo "########################################################################################"
	@echo "## mvn dependency:tree"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn dependency:tree

kill-template:
	@sudo kill -9 $(sudo lsof -t -i:8080) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9080) > /dev/null 2>&1
