# ![Karumi logo][karumilogo] Kata Coroutines in Kotlin

- We are here to practice the usage of [Coroutines][coroutines].
- We are going to use [Kotlin][kotlin].
- We are going to practice pair programming.

---

## Getting started

This repository contains a Kotlin implementation of the [Conway's Game of Life][gameoflife]

![terminal][terminal]

The code is really simple and it's just a naive implementation of the algorithm. It is sequential, that means that for every single generation it calculates the value of each cell for the next generation, one by one. 

To verify the correct behaviour of your code you can execute:

```shell
./gradlew runGameOfLife
```

## Tasks

Your task as a Kotlin Developer is to **transform the implementation to make it parallel**. You will use coroutines to achieve it.

## Considerations

If you get stuck, `with-coroutines` branch contains the finished kata.

---

## Documentation

There are some links which can be useful to finish these tasks:

## License

Copyright 2019 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[kotlin]: https://kotlinlang.org/
[coroutines]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[gameoflife]: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
[terminal]: ./art/terminal.png
[demystifyingcoroutinecontext]: https://proandroiddev.com/demystifying-coroutinecontext-1ce5b68407ad
