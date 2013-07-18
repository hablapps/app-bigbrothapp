## What is Big Brothapp?

Big Brothapp is a reality game show whose premise is a group of
contestants living together in a large house, isolated from the
outside world and governed by the Brotha, the eye that sees it
all. Each series lasts for about three months, with 12–16
contestants entering the house. To win the final cash prize, a
contestant must survive periodic evictions and be the last one
standing.

At regular intervals, the housemates privately
nominate (providing a cause) a fellow housemate whom they wish
nominee to leave the house. The housemates with the most
nominations are then announced, and viewers are given the
opportunity to vote for whom they wish to see evicted. The Brotha
is responsible for starting the nomination phase of an
eviction. The polling phase starts just after all the housemates
have nominated. Finally, the Brotha determines the end of the
polling process. After the votes are tallied, the evictee leaves
the house. It may worth mentioning that a housemate cannot leave
the house while an eviction process is opened. On the other hand,
the Brotha is empowered to fire a contestant if he behaves
improperly.

At the end of the game, the last remaining housemate is declared
the winner for the particular series and receives the prize.

## Run Online

Follow the instructions on the [apps](http://speechlang.org/apps.php)
section from speechlang.org.

## Compilation

To compile the bigbrothapp source code simply follow these steps:

#### Download bigbrothapp

To download these sources, you must obtain [git](http://git-scm.com/)
and clone the app-bigbrothapp repository.

```shell 
> git clone https://github.com/hablapps/app-bigbrothapp.git
```

#### Install sbt 

The app-bigbrothapp project is configured with the sbt build tool. To
install sbt follow the instructions at <https://github.com/sbt/sbt>.

#### Compile bigbrothapp

```shell
$ cd <bigbrothapp>
$ sbt 
> test:compile
```

## Run Big Brothapp

From sbt, you can run some tests with the `test` command:

```shell
> test-only org.hablapps.bigbrothapp.test.All_BigBrothapp
...
```

## License

This software is released under Apache License, Version 2.0.

A "Big Brotha" implemented in Speech.
