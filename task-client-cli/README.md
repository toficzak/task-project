# Task Client CLI

## Desc

Client CLI to task-server.

# Features

- context - allows persisting task list/task in custom file caches

# Support

- task-server:0.0.2
- provides:
    - task lists creation
    - tasks creation
    - comments creation
- lacks:
    - comments removing

# Build

`
docker build -t task-client-cli:0.0.1 .
`

# Run

`
docker stop task-client-cli;
docker rm task-client-cli;
docker run -it -d --name task-client-cli --network task-deploy_default --env tcc_base_url=http://task-server:8080 task-client-cli:0.0.1
`
