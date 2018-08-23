#!/bin/sh

lein do clean, uberjar
lein heroku deploy
