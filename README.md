## Big Brothapp 

This an implementation of the Twitter social network in the [Speech]
(http://speechlang.org) programming language. The purpose of this
implementation is simply to illustrate how the *functional
requirements* of typical Web 2.0 apps can be programmed in
Speech. Thus, we abstract away from non-functional concerns relating
to the persistence, presentation and web layers, and focus instead on
the business logic of Twitter. You can find explanations on the design
of this app in the Speech [user
guide](http://speechlang.org). Currently, the implementation is far
from being complete, but it will eventually cover the 100% of these
requirements. We promise!

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

Follow the instructions on [speechlang.org](http://speechlang.org/apps.php)

## Compilation

To compile the bigbrothapp source code simply follow these steps:

#### Download bigbrothapp

To download these sources, you must obtain [git](http://git-scm.com/)
and clone the app-bigbrothapp repository.

```shell 
> git clone https://github.com/hablapps/app-bigbrothapp.git
```

#### Install scala 

Speech is implemented as an embedded DSL in Scala, so you must
download it first. Follow the instructions at <http://scala-lang.org>.

#### Install sbt 

The app-twitter project is configured with the sbt build tool. To
install sbt follow the instructions at <https://github.com/sbt/sbt>.

#### Install speech 

Download the Speech interpreter from the following address:
<http://speechlang.org>. Then, simply create a `<bigbrothapp>/lib`
directory, and install there the `speech.jar` archive.

#### Compile twitter 

```shell
$ cd <bigbrothapp>
$ sbt 
> test:compile
```

## Run Big Brothapp

From sbt, you can run some tests with the `test` command:

```shell
> test
...
```

## License

This software is released under Apache License, Version 2.0.

A "Big Brotha" implemented in Speech.
