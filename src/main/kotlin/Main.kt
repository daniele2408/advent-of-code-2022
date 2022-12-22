import solutions.day10cathodraytube.printScreen
import solutions.day10cathodraytube.sumSignals
import solutions.day11monkeyinthemiddle.levelOfMonkeyBusinessAfterNRounds
import solutions.day11monkeyinthemiddle.levelOfMonkeyBusinessAfterNRoundsHighStress
import solutions.day12hillclimbingalgorithm.fewestStepRequiredFromNearestLowestPoint
import solutions.day13distresssignal.getDecoderKey
import solutions.day13distresssignal.sumOfIndicesOfRightOrderedPairs
import solutions.day14regolithreservoir.howMuchSandResting
import solutions.day14regolithreservoir.howMuchSandRestingHavingFloor
import solutions.day01caloriecounting.getCaloriesTopNElves
import solutions.day02rockpaperscissor.computeTotalScore
import solutions.day02rockpaperscissor.computeTotalScore2
import solutions.day03rucksackorganization.sumPrioritiesItemsGroupOfThree
import solutions.day03rucksackorganization.sumPrioritiesItemsRucksack
import solutions.day04campcleanup.countPartiallyOverlappingRanges
import solutions.day04campcleanup.countTotallyOverlappingRanges
import solutions.day05supplystacks.getCratesOnTop
import solutions.day05supplystacks.model.CraneModel
import solutions.day06tuningtrouble.findMarkerPosition
import solutions.day06tuningtrouble.model.ProtocolMarker
import solutions.day07nospaceleftondevice.getSizeFolderToDelete
import solutions.day07nospaceleftondevice.getTotalSizeDirectories
import solutions.day08treetoptreehouse.countVisibleTrees
import solutions.day08treetoptreehouse.findBestScenicScore
import solutions.day09ropebridge.totalPositionRopeTailVisited
import solutions.day09ropebridge.totalPositionTailVisited
import solutions.day15beaconexclusionzone.findTuningFrequency
import solutions.day15beaconexclusionzone.positionExcludingBeacon

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
    println("We have to process ${findMarkerPosition(ProtocolMarker.START_OF_PACKET)} characters before the first start-of-packet marker is detected")
    println("We have to process ${findMarkerPosition(ProtocolMarker.START_OF_MESSAGE)} characters before the first start-of-message marker is detected")

    println("############## DAY 7 ##############")
    println("Sum of directories' size is ${getTotalSizeDirectories()}")
    println("Size of smallest folder I'd have to free enough space is ${getSizeFolderToDelete()}")

    println("############## DAY 8 ##############")
    println("There are ${countVisibleTrees()} visible trees from outside the grid")
    println("The highest scenic score possible is ${findBestScenicScore()}")

    println("############## DAY 9 ##############")
    println("Tail of the rope visited ${totalPositionTailVisited()} positions at least once")
    println("Tail of the rope having 9 knots visited ${totalPositionRopeTailVisited()} positions at least once")

    println("############## DAY 10 ##############")
    println("Sum of these six signal strengths is ${sumSignals(listOf(20, 60, 100, 140, 180, 220))}")
    println("CRT screen is showing following output:\n\n${printScreen(listOf(20, 60, 100, 140, 180, 220))}\n\n")

    println("############## DAY 11 ##############")
    println("Monkey business level after 20 rounds is ${levelOfMonkeyBusinessAfterNRounds(20)}")
    println("Monkey business level after 10000 rounds is ${levelOfMonkeyBusinessAfterNRoundsHighStress(10000)}")

    println("############## DAY 12 ##############")
    val (shortestPath1, shortestPath2) = fewestStepRequiredFromNearestLowestPoint()
    println("Fewest step required to move from current position to the location is $shortestPath1")
    println("Fewest step required to move from lowest nearest position to the location is $shortestPath2")

    println("############## DAY 13 ##############")
    println("The sum of the indices of pairs in right order is ${sumOfIndicesOfRightOrderedPairs()}")
    println("The decoder key is ${getDecoderKey()}")

    println("############## DAY 14 ##############")
    println("There are ${howMuchSandResting()} grains of sand resting.")
    println("There are ${howMuchSandRestingHavingFloor()} grains of sand resting once we introduce floor.")

    println("############## DAY 15 ##############")
    val y = 2_000_000
    println("There are ${positionExcludingBeacon(y)} positions that cannot contain a beacon for y = $y")
    println("Tuning frequency for lost distress beacon is ${findTuningFrequency(0, 4_000_000)}")

}