Docker need to be installed: 
run in console `docker run --rm -P -p 127.0.0.1:5432:5432  -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpassword -e POSTGRES_DB=question -d postgres:alpine`
and then run application with `gradlew bootRun`


Problem definition

Create a tiny RESTful web service with the following business requirements:

    Application must expose REST API endpoints for the following functionality:
        ask question
        list all accepted questions
        list all accepted questions by country code
    User should be able to ask question publicly by providing question text.
    Service must perform question validation according to the following rules and reject question if:
        Question contains blacklisted words listed in a dictionary
        N questions / second are asked from a single country (essentially we want to limit number of questions coming from a country in a given timeframe)
    Service must perform origin country resolution using the following web service and store country code together with the question. Because networking is unreliable and services tend to fail, let's agree on default country code - "lv".