# navi

ナビィは、チームをSlack越しに支えてくれる妖精です。

## Before you start

Add heroku remote repository.

    git remote add heroku https://git.heroku.com/boiling-stream-79705.git

Install [heroku toolbelt](https://devcenter.heroku.com/articles/heroku-cli#download-and-install).

## Development

To start application on your REPL, run

    (go)

You will get 200 OK from

    http://localhost:3000

You can see `swagger.yaml` to check API specification.

## Deployment

To deploy to heroku, run

    ./deploy.sh
