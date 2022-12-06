import model.CraneModel
import model.ProtocolMarker
import solutions.*

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

    println("############## DAY 4 ##############")
    println("There are ${countTotallyOverlappingRanges()} tasks totally overlapping each other")
    println("There are ${countPartiallyOverlappingRanges()} tasks partially overlapping each other")

    println("############## DAY 5 ##############")
    println("After instructions, following crates will end up on top ${getCratesOnTop(CraneModel.CRATE_MOVER_9000)} if using crane model ${CraneModel.CRATE_MOVER_9000}")
    println("After instructions, following crates will end up on top ${getCratesOnTop(CraneModel.CRATE_MOVER_9001)} if using crane model ${CraneModel.CRATE_MOVER_9001}")

    println("############## DAY 6 ##############")
    println("We have to process ${findMarkerPosition(ProtocolMarker.START_OF_PACKET)} character before the first start-of-packet marker is detected")
    println("We have to process ${findMarkerPosition(ProtocolMarker.START_OF_MESSAGE)} character before the first start-of-message marker is detected")
}