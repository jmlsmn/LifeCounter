package LifeCounter.Models;

public class LifeAdjustment {
	
	int _total;
	LifeAdjustmentType _type;
	int _adjustment;
	
	public LifeAdjustment()
	{}
	
	public LifeAdjustment(LifeAdjustmentType type, int total, int adjustment)
	{
		_type = type;
		_total = total;
		_adjustment = adjustment;
	}
	
	public int get_total() {
		return _total;
	}
	public void set_total(int _total) {
		this._total = _total;
	}
	public LifeAdjustmentType get_type() {
		return _type;
	}
	public void set_type(LifeAdjustmentType _type) {
		this._type = _type;
	}
	public int get_adjustment() {
		return _adjustment;
	}
	public void set_adjustment(int _adjustment) {
		this._adjustment = _adjustment;
	}
}
