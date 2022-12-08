import solutions.day1caloriecounting.getCaloriesTopNElves
import solutions.day2rockpaperscissor.computeTotalScore
import solutions.day2rockpaperscissor.computeTotalScore2
import solutions.day3rucksackorganization.sumPrioritiesItemsGroupOfThree
import solutions.day3rucksackorganization.sumPrioritiesItemsRucksack
import solutions.day4campcleanup.countPartiallyOverlappingRanges
import solutions.day4campcleanup.countTotallyOverlappingRanges
import solutions.day5supplystacks.getCratesOnTop
import solutions.day5supplystacks.model.CraneModel
import solutions.day6tuningtrouble.findMarkerPosition
import solutions.day6tuningtrouble.model.ProtocolMarker
import solutions.day7nospaceleftondevice.getSizeFolderToDelete
import solutions.day7nospaceleftondevice.getTotalSizeDirectories
import solutions.day8treetoptreehouse.countVisibleTrees
import solutions.day8treetoptreehouse.findBestScenicScore

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

}