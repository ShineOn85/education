package legion;


import my.list.MyList;

public class Legion {
    public MyList<Integer> kill(int size, int step, MyList<Integer> legion) {
        final int stepConst = step;

        for (int i = 1; i <= size; i++)
            legion.add(i);

        for (int i = 0; stepConst != legion.size() + 1; i++) {
            if (i == step) {
                step--;
                legion.remove(step);
                step += stepConst;
                if (step > legion.size()) {
                    step -= legion.size();
                    i = 0;
                }
            }
        }
        //System.out.println(legion);
        return legion;
    }

}
