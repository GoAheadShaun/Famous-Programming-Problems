class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<Point> points = new ArrayList<>();
        for (int[] b: buildings) {
            points.add(new Point(b[0],b[2],'s'));
            points.add(new Point(b[1],b[2],'e'));
        }
        
        sortPoints(points);
        
        List<List<Integer>> skyLine = new ArrayList<>();
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(0,1);

        for (int i=0; i<points.size(); i++) {
            Point curr = points.get(i);
            if (curr.type == 's') {
                if (curr.h > map.firstKey()) 
                    addPoint(skyLine, curr.x, curr.h);
                map.put(curr.h, map.getOrDefault(curr.h,0) + 1);
            }
            else {
                map.put(curr.h, map.get(curr.h) - 1);
                if (map.get(curr.h) == 0) {
                    map.remove(curr.h);
                    if (curr.h > map.firstKey()) 
                        addPoint(skyLine, curr.x, map.firstKey());
                }   
            }
        }
        
        return skyLine;
    }
    
    private void sortPoints(List<Point> points) {
        Collections.sort(points, new Comparator<Point>() {
            public int compare(Point a, Point b) {
                if (a.x == b.x) {
                    if (a.type == 'e' && b.type == 'e') 
                        return a.h - b.h;
                    else if (a.type == 'e')
                        return 1;
                    else if (b.type == 'e')
                        return -1;
                    return b.h - a.h;
                }
                return a.x - b.x;
           }
        });
    }
    
    private void addPoint(List<List<Integer>> skyLine, int x, int h) {
        List<Integer> newPoint = new ArrayList<>();
        newPoint.add(x);
        newPoint.add(h);
        skyLine.add(newPoint);
    }
}