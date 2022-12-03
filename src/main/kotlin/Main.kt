fun main(args: Array<String>) {
    println("############## DAY 1 ##############")
    println("The Elf carrying more calories is carrying ${getCaloriesTopNElves(1)} calories")
    println("The top 3 Elves carrying more calories are carrying ${getCaloriesTopNElves(3)} calories")

    println("############## DAY 2 ##############")
    println("Using first strategy guide, my total score would be ${computeTotalScore()} points")
    println("Using second strategy guide, my total score would be ${computeTotalScore2()} points")

    println("############## DAY 3 ##############")
    println("The sum of the priorities of shared item in rucksacks' compartment is ${sumPrioritiesItemsRucksack()}")
    println("The sum of the priorities of item type corresponding to each group's badge is ${sumPrioritiesItemsGroupOfThree()}")

}