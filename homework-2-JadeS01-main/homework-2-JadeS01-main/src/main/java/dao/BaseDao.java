package dao;

import dto.BasePaymentDto;
import java.util.List;

public interface BaseDao<T extends BasePaymentDto> {

  public void put(T t);

  public T get(String id);

  List<T> getAll();
}
