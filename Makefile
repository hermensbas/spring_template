.PHONY: help compile test-unit test-int test-all build-opera run-opera build-greeting run-greeting docker-up docker-down versions-show versions-update kill-opera kill-greeting

CURRENT_DIR = $(realpath .)

help:
	@echo "Available targets for:"
	@echo "########################################################################################"
	@echo "  - help\t\t\tThis help"
	@echo "  - compile\t\t\tCompiles code"
	@echo "  - test\t\t\tUnit-tests"
	@echo "  - test-int\t\t\tIntegration-tests"
	@echo "  - test-all\t\t\tUnit and integration tests"
	@echo "  - build-opera\t\t\tBuilds opera app"
	@echo "  - run-opera\t\t\tRuns opera app"
	@echo "  - build-greeting\t\tBuilds greeting app"
	@echo "  - run-greeting\t\tRuns greeting app"
	@echo "  - docker-up\t\t\tRuns docker"
	@echo "  - docker-down\t\t\tStops docker"
	@echo "  - versions-show\t\tShows outdated POM versions"
	@echo "  - versions-update\t\tUpdate POM versions"
	@echo "  - deps\t\t\tShows dependency tree"
	@echo "  - kill-opera\t\t\tKills app-opera process"
	@echo "  - kill-greeting\t\tKills app-greeting process"
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

build-opera:
	@echo "########################################################################################"
	@echo "## mvn -pl app-opera -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl app-opera -am clean -Dskip.unit.tests=true package

run-opera:
	@echo "########################################################################################"
	@echo "## mvn -pl app-opera -am clean spring-boot:run"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl app-opera -am clean spring-boot:run

build-greeting:
	@echo "########################################################################################"
	@echo "## mvn -pl app-greeting -am clean -Dskip.unit.tests=true package"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl app-greeting -am clean -Dskip.unit.tests=true package

run-greeting:
	@echo "########################################################################################"
	@echo "## mvn -pl app-greeting -am clean spring-boot:run"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@clear
	@mvn -pl app-greeting -am clean spring-boot:run

docker-up:
	@echo "########################################################################################"
	@echo "## Run docker compose containers"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker compose -f .docker/compose.yml -p myapp up -d

docker-down:
	@echo "########################################################################################"
	@echo "## Stop docker compose containers"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@docker compose -f .docker/compose.yml -p myapp down -v


# Checks to see if there are newer versions of dependencies or plugins
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

# Shows dependency tree
deps:
	@echo "########################################################################################"
	@echo "## mvn dependency:tree"
	@echo "########################################################################################"
	@echo "########################################################################################"
	@mvn dependency:tree


kill-opera:
	@sudo kill -9 $(sudo lsof -t -i:8080) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9080) > /dev/null 2>&1

kill-greeting:
	@sudo kill -9 $(sudo lsof -t -i:8081) > /dev/null 2>&1
	@sudo kill -9 $(sudo lsof -t -i:9081) > /dev/null 2>&1
