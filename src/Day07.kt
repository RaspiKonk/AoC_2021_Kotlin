/**
 * AoC 2021 Day 7 - crabs in submarines are helping me
 * Part 1: find the position where all crabs can move using the least fuel of their submarine. each step need one fuel
 * Part 2: fuel consumption of step 1 is 1, step 2 is 2, ...
 */


import java.util.*  // for Collections - probably also doable with pure Kotlin


fun main()
{
	/**
	 * one step costs one fuel
	 */
	fun getOverallFuelAtThisPosition(crabPositionMap:MutableMap<Int, Int>, posToCheck: Int):Int
	{
		var fuel:Int = 0
		for(position in crabPositionMap)
		{
			var localFuel = position.value * (Math.abs(position.key - posToCheck));
			fuel += localFuel

		}
		return fuel
	}

	/**
	 * 1. step costs 1 fuel, 2nd step 2 fuel, ...
	 */
	fun getOverallFuelAtThisPositionPart2(crabPositionMap:MutableMap<Int, Int>, posToCheck: Int):Int
	{
		var fuel:Int = 0
		for(position in crabPositionMap)
		{
			var distance = Math.abs(position.key - posToCheck)
			var stepCost = 0;
			//println("position: " + position.key + " moveTo: " + posToCheck + " stepCost: ")
			for(i in 0.. distance)
			{
				stepCost += i
			}
			var localFuel = position.value * stepCost;
			fuel += localFuel

		}
		return fuel
	}


	fun part1(input: List<String>): Int
	{
		var parts = input[0].split(",")
		println("Crab positions: " + parts)
		// create a Map if integers with the layout:
		// position - number of crabs at that position
		val crabPositionMap = mutableMapOf<Int, Int>()
		for(singleCrab in parts)
		{
			//println(singleCrab)
			val num = crabPositionMap.get(singleCrab.toInt())
			if(num != null)
			{
				crabPositionMap.put(singleCrab.toInt(), num + 1)
			}
			else
			{
				crabPositionMap.put(singleCrab.toInt(), 1)
			}
		}
		//println(crabPositionMap) // {16=1, 1=2, 2=3, 0=1, 4=1, 7=1, 14=1}

		// now go through the array and calculate how much fuel is needed if all crabs go to this position
		// NB: also consider the array places where there is currently to crab
		var maxCrabPosition = Collections.max(crabPositionMap.keys)
		println("maxCrabPosition :" + maxCrabPosition)

		val fuelConsumptionMap = mutableMapOf<Int, Int>() // position - fuel consumption
		//for(position in crabPositionMap)
		for(i in 0..maxCrabPosition)
		{
			//println(position.key.toString() + " " + position.value.toString())
			var fuelAtThisPosition = 0
			for(entry in crabPositionMap)
			{
				fuelAtThisPosition = getOverallFuelAtThisPosition(crabPositionMap, i)
			}
			fuelConsumptionMap.put(i, fuelAtThisPosition)
			//println("Position to check: " + i.toString() + " fuel needed: " + fuelAtThisPosition)
		}
		//println(fuelConsumptionMap)
		val minValueInMap: Int = Collections.min(fuelConsumptionMap.values)
		val positionWithLeastConsumption = fuelConsumptionMap.filterValues { it == minValueInMap }.keys
		println("Positon with lowest value: " + positionWithLeastConsumption + " fuel used: " + minValueInMap)

		return minValueInMap  // return lowest fuel used
	}


	/**
	 * Determine the horizontal position that the crabs can align to using the least fuel possible so
	 * they can make you an escape route! How much fuel must they spend to align to that position?
	 */
	fun part2(input: List<String>): Int
	{
		// better way to read everything into an array of Integers instead of strings:
		val positions = input.first().split(",").map { it.toInt() }
		println(positions)

		// same as part one without the toInt()
		val crabPositionMap = mutableMapOf<Int, Int>()
		for(singleCrab in positions)
		{
			val num = crabPositionMap.get(singleCrab)
			if(num != null){
				crabPositionMap.put(singleCrab, num + 1)}
			else{
				crabPositionMap.put(singleCrab, 1)}
		}
		println("crabPositionMap: " + crabPositionMap)  // how many crabs are at which position?
		val sortedMap: MutableMap<Int, Int> = LinkedHashMap()
		crabPositionMap.entries.sortedBy { it.key }.forEach { sortedMap[it.key] = it.value }
		//println(sortedMap)

		//crabPositionMap.entries.sortedBy { it.key }.forEach { crabPositionMap[it.key] = it.value}
		println("sortedMap: " + sortedMap)

		var maxCrabPosition = Collections.max(crabPositionMap.keys)
		println("maxCrabPosition :" + maxCrabPosition)





		val fuelConsumptionMap = mutableMapOf<Int, Int>() // position - fuel consumption
		//for(position in crabPositionMap)
		for(i in 0..maxCrabPosition)
		{
			//println(position.key.toString() + " " + position.value.toString())
			var fuelAtThisPosition = 0
			for(entry in crabPositionMap)
			{
				fuelAtThisPosition = getOverallFuelAtThisPositionPart2(crabPositionMap, i)
			}
			fuelConsumptionMap.put(i, fuelAtThisPosition)
			println("Position to check: " + i.toString() + " fuel needed: " + fuelAtThisPosition)
		}
		//println(fuelConsumptionMap)
		val minValueInMap: Int = Collections.min(fuelConsumptionMap.values)
		val positionWithLeastConsumption = fuelConsumptionMap.filterValues { it == minValueInMap }.keys
		println("Position with lowest value: " + positionWithLeastConsumption + " fuel used: " + minValueInMap)






		return minValueInMap
	}


	// test if implementation meets criteria from the description, like:
	val testInput = readInput("puzzles/Day07_test")
	//check(part1(testInput) == 1)

	val input = readInput("puzzles/Day07")
	println("AoC 2021 Day 7 in Kotlin")
	//println("Part 1: Position with the least fuel burnt: " + part1(testInput))
	//println("Part 1: Least fuel burnt: " + part1(input))  // 336131

	//println("Part 2: Least fuel burnt: " + part2(testInput))
	println("Part 2: Least fuel burnt: " + part2(input))  // took a long time to compute: 92676646 (position 473)
}
