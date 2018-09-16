# NeuralNetworkSnake

Simple terminal snake game, which is played by neural network.

## Overview

This project consists of three subprojects:

snake game - simple terminal snake game

neural network - configurable neural network which holds strategy how to play snake

genetic algorithm - is responsible for teaching neural network how play every time app is started

## How it works

When application starts, genetic algorithm (GA) creates population (population zero) of 50 individuals (neural networks - NN) with randomly set weights.

Then GA lets play every NN in population snake game 100 times and rate every NN with number describing how good it was. This number is called fitness and in this case is similar to game score.

After that, GA pics best networks (selection) and cross them together (crossing). Also randomly mutate few attributes of NN (mutation).

Result of this process is population of 50 individuals created by using principles of evolution.

This process repeats again and again to get individuals with better fitness than their parrents had.

When you run the program, at first you can see actual population number with fitness value of best NN in current population.

GA contains strategy called elitism, which means, that the best individual from parent population is cloned to next population to avoid losing good result. Because of this you can see that every next population has same or bigger fitness than population before.

After 100 generations performed, best NN is picked and finally starts  playing snake.

## How to run

Download this repo as a ZIP, or clone by "git clone [repo name]" command.

Go to repo main directory and build project with command "gradle clean build". You have to install gradle build tool first.

After that, in folder build/libs/ run generated jar file by typing: "java -jar [filename]".

You can also run the app directly from gradle by typing: "gradle run -q" (-q disables unwanted gradle text output).

In both ways mentioned above you actually run class called NnMain.java. You can also run from IDE class Main.java, which allows you play snake by yourself.

## License

This project is licensed under the GPL-3.0 License - see the [LICENSE](LICENSE) file for details
