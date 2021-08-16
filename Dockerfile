FROM wpilib/ubuntu-base:18.04

COPY test_runner /test/
RUN chmod +x /test/run.sh
RUN chmod +x /test/gradlew

ENTRYPOINT ["/test/run.sh"]
