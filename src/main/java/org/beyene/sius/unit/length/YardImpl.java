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
package org.beyene.sius.unit.length;

import org.beyene.sius.dimension.Length;
import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.UnitIdentifier;

final class YardImpl implements Yard {

	private final double scalar;
	private static final UnitId<Length, Meter, Yard> unitId = UnitIdentifier.YARD;
	
	public YardImpl(double scalar) {
		this.scalar = scalar;
	}

	@Override
	public Length getDimension() {
		return Length.INSTANCE;
	}

	@Override
	public UnitId<Length, Meter, Yard> getIdentifier() {
		return unitId;
	}

	@Override
	public <O extends Unit<Length, Meter, O>> Yard convert(O other) {
		Yard converted;
		if (other.getIdentifier().equals(unitId))
			converted = LengthFactory.yard(other.getScalar());
		else if (other.getIdentifier().equals(UnitIdentifier.METER))
			converted = LengthFactory.yard(other.getScalar() / Constants.METER_PER_YARD);
		else
			converted = Operation.convert(other, unitId);
		return converted;
	}

	@Override
	public Meter toBaseUnit() {
		return LengthFactory.meter(scalar * Constants.METER_PER_YARD);
	}

	@Override
	public Yard valueOf(double scalar) {
		return LengthFactory.yard(scalar);
	}

	@Override
	public double getScalar() {
		return scalar;
	}
	
	@Override
	public String toString() {
		return "Yard [value=" + scalar + "]";
	}
}