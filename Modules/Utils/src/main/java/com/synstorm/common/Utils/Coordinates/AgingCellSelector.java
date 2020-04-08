package com.synstorm.common.Utils.Coordinates;

import gnu.trove.set.TIntSet;

import java.util.*;

public class AgingCellSelector extends CellSelector {
    private LinkedList<Integer> query;
    private boolean alive;

    AgingCellSelector(Space sp) {
        super(sp);
        alive = updateQuery();
    }

    public boolean isAlive() {
        return alive;
    }

    public ArrayList<Integer> getCells(double ratio, TIntSet valid, Random rnd) {
        int maxCount = (int) ratio;
        ArrayList<Integer> res = new ArrayList<>();

        for(Iterator it = query.iterator(); it.hasNext() && res.size() < maxCount;) {
            Integer idx = (Integer) it.next();

            if (valid.contains(idx)) {
                res.add(idx);
                it.remove();
            }
        }

        // получаем новый список клеток
        if(res.size() < maxCount) {
            updateQuery();

            // нужно всего два цикла.
            // Первый - начальная проверка
            // Второй - с обновленной очередью
            // Если добавитьв  while, то можно бегать по вечному обновлению списка клеток
            // Хотя доступных и валидных клеток попросту нет
            for(Iterator it = query.iterator(); it.hasNext() && res.size() < maxCount;) {
                Integer idx = (Integer) it.next();

                if (valid.contains(idx)) {
                    res.add(idx);
                    it.remove();
                }
            }
        }

        return res;
    }

    public boolean updateQuery() {
        query = new LinkedList<>(sp.getCellIds());
        Collections.shuffle(query);

        alive = !query.isEmpty();
        return alive;
    }
}
