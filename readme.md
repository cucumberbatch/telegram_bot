# Telegram Bot

A Java telegram bot on postgres database that provides tag system for subscriber phone numbers, that will helps to define spam calls or something else for other people by entering a phone number of incoming call. It's something like getcontact but this bot works other way:
1. user that find out that concrete call was a spam will add that phone number and tag by hand
2. if other user has an incoming call, he dont want to pick up the phone, then he just goes to bot chat and reqests info about that number if it is already in database 