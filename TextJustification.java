class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        
        int n = words.length, d = 0, left = 0, right = 0;
        while (right < n) {
            if (d != 0) d++;
            d += words[right].length();
            
            if (d > maxWidth) { 
                result.add(formatLine(words, maxWidth, left, right-1));
                left = right;
                d = 0;
            }
            else if (right == n-1) {
                result.add(formatLastLine(words, maxWidth, left, right));
                break;
            }
            else right++;
        }
        
        return result;
    }
    
    
    public String formatLine(String[] words, int maxWidth, int l, int r) {
        StringBuilder sb = new StringBuilder();
        
        int len = 0;
        for (int i=l; i<=r; i++) 
            len += words[i].length();
        
        if (r-l == 0) {
            sb.append(words[l]);
            int dist = maxWidth - words[l].length();
            for (int i=0; i<dist; i++) 
                sb.append(" ");
        }
        else {
            int spacing = (int) Math.floor((maxWidth-len)/(r-l));
            int d = 0;

            for (int i=l; i<=r; i++) {
                sb.append(words[i]);
                if (i != r) {
                    d += words[i].length();
                    for (int j=0; j<spacing; j++) 
                        sb.append(" ");
                    d -= spacing;
                }
            }

            int index = 0;
            boolean seenChar = false;
            while (sb.length() < maxWidth) {
                if (sb.charAt(index) != ' ') {
                    seenChar = true;
                }
                else if (seenChar && sb.charAt(index) == ' ') {
                    sb.insert(index, " ");
                    seenChar = false;
                }
                index++;
            }
        }
        
        return sb.toString();
    }
    
    
    public String formatLastLine(String[] words, int maxWidth, int l, int r) {
        StringBuilder sb = new StringBuilder();
        
        for (int i=l; i<=r; i++) {
            sb.append(words[i]);
            if (i != r) sb.append(" ");
        }
        
        int spaces = maxWidth - sb.length();
        for (int i=0; i<spaces; i++) 
            sb.append(" ");
        
        return sb.toString();
    }    
    
}
