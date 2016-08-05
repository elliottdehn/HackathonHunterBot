# HackathonHunterBot
This is our best attempt at a well structured script for a fairly complicated task

Some of the code is rather stale and looking back I'd make a few changes (such as the structure).
The premise of the script is essentially a loop which checks if certain conditions are met in certain parts of the map. If those conditions are met, actions to handle the conditions are queued.
This creates a situation where the bot can essentially function forever and handle as many situations as I please. I could have also added failure states which would allow the bot to return to what it was doing if anything went wrong.
