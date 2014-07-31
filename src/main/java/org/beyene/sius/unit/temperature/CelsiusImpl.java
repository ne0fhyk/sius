/*
 * Copyright 2014 Mikael Beyene
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.beyene.sius.unit.temperature;

import org.beyene.sius.dimension.Temperature;
import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;

final class CelsiusImpl implements Celsius {

	private final double scalar;
	private static final UnitId<Temperature, Kelvin, Celsius> unitId = UnitIdentifier.CELSIUS;
	
	public CelsiusImpl(double scalar) {
		this.scalar = scalar;
	}

	@Override
	public Temperature getDimension() {
		return Temperature.INSTANCE;
	}

	@Override
	public UnitId<Temperature, Kelvin, Celsius> getIdentifier() {
		return unitId;
	}

	@Override
	public <OTHER extends Unit<Temperature, Kelvin, OTHER>> Celsius convert(OTHER other) {
		Celsius converted;
		if (other.getIdentifier().equals(unitId))
			converted = TemperatureFactory.celsius(other.getScalar());
		else if (other.getIdentifier().equals(UnitIdentifier.KELVIN))
			converted = TemperatureFactory.celsius(other.getScalar() - Constants.CELSIUS_KELVIN_OFFSET);
		else
			converted = Operation.convert(other, unitId);
		
		return converted;
	}

	@Override
	public Kelvin toBaseUnit() {
		return TemperatureFactory.kelvin(scalar + Constants.CELSIUS_KELVIN_OFFSET);
	}

	@Override
	public Celsius valueOf(double d) {
		return TemperatureFactory.celsius(d);
	}

	@Override
	public double getScalar() {
		return scalar;
	}

	@Override
	public String toString() {
		return "Celsius [value=" + scalar + "]";
	}
}