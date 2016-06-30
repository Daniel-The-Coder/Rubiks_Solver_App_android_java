package com.hfad.rubikssolver;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Lord Daniel on 5/28/2016.
 */
public class CubeConfig implements Configuration{

    public HashMap<String, char[][]> cube;
    public ArrayList<String> moves;
    public HashMap<String,String > oppMoves;
    public int dim;
    public int depth;
    public static ArrayList<String> faces;
    public static ArrayList<Character> directions;




    /**
     * To create a new cube configuration from a file
     *
     * file format:
     *          XXX (top)
     *          XXX (front)
     *   (left) XXX
     *          XXX (right)
     *          XXX (bottom)
     *          XXX (back)
     *
     * @param cube
     */
    public CubeConfig(HashMap<String, char[][]> cube) {
        this.dim=3;
        this.depth = 0;
        this.moves = new ArrayList<>();
        this.oppMoves = new HashMap<>();
        oppMoves.put("c","a");
        oppMoves.put("a","c");

        this.faces = new ArrayList<>();
        this.faces.add("top");
        this.faces.add("front");
        this.faces.add("left");
        this.faces.add("right");
        this.faces.add("bottom");
        this.faces.add("back");

        this.directions = new ArrayList<>();
        this.directions.add('c');
        this.directions.add('a');

        this.cube=cube;


    }

    /**
     * copy constructor
     */
    public CubeConfig(CubeConfig other){
        this.cube = new HashMap<>();
        for (String face:other.cube.keySet()){
            char[][] ar = new char[other.dim][other.dim];
            for (int i=0;i<other.dim;i++){
                for (int j=0;j<other.dim;j++){
                    ar[i][j] = new Character(other.cube.get(face)[i][j]);
                }
            }
            this.cube.put(face,ar);
        }
        this.faces=other.faces;
        this.directions=other.directions;
        this.depth = other.depth + 1;
        this.dim=other.dim;
        this.moves = new ArrayList<>(other.moves);
        this.oppMoves = other.oppMoves;

    }

    /**
     * turn a 2-dimensional array of characters anticlockwise by 90 degrees
     * @param Face
     * @return
     */
    public char[][] transpose(char[][] Face){
        char[][] newFace = new char[dim][dim];
        for (int i=0; i<dim;i++){
            for (int j=0;j<dim;j++){
                newFace[dim-j-1][i] = Face[i][j];
            }
        }
        return newFace;
    }

    /**
     * turn a face clockwise or anticlockwise
     * DOES NOT AFFECT EDGES ON OTHER FACES
     * @param face
     * @param direction
     */
    public void turnFace(String face, char direction){
        //clockwise
        if (direction=='c'){
            for (int i=0;i<3;i++){
                char[][] Face = cube.get(face);
                cube.put(face, transpose(Face));
            }
        }
        //anticlockwise
        else{
            char[][] Face = cube.get(face);
            cube.put(face, transpose(Face));
        }
    }

    public void swapTwoElements(Object E1, Object E2){
        Object temp = E1;
        E1 = E2;
        E2 = temp;
    }

    public ArrayList<Object> swapFourElements(Object E1, Object E2, Object E3, Object E4){
        ArrayList<Object> ar = new ArrayList<>();
        ar.add(E1); ar.add(E2); ar.add(E3); ar.add(E4);
        //System.out.println("before swap: "+ar);
        ar.add(E4); //Object temp = E4;
        ar.set(3, ar.get(2)); //E4 = E3;
        ar.set(2, ar.get(1)); //E3 = E2
        ar.set(1, ar.get(0)); //E2 = E1
        ar.set(0, ar.get(4)); //E1 = temp;
        ar.remove(4);
        //System.out.println("after swap: "+ar);
        return ar;
    }


    /**
     *  Execute specified move
     *
     *  transpose the face being turned
     *  swap the edges, i.e. next edge gets values on current edge
     *
     * @param layer 1, 2 or 3 - counting left to right, top to bottom or back to front
     * @param direction - clockwise (c) and anticlockwise (a)
     */
    public void move(String layer, char direction){
        //turn the face
        turnFace(layer, direction);

        //change edges
        if (layer.equals( "right" )){
            char[][] front = cube.get("front");
            char[][] bottom = cube.get("bottom");
            char[][] back = cube.get("back");
            char[][] top = cube.get("top");
            if (direction=='c'){
                ArrayList lst1 = swapFourElements( cube.get("front")[0][2], cube.get("top")[0][2], cube.get("back")[0][2], cube.get("bottom")[0][2] );
                ArrayList lst2 = swapFourElements( cube.get("front")[1][2], cube.get("top")[1][2], cube.get("back")[1][2], cube.get("bottom")[1][2] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][2], cube.get("top")[2][2], cube.get("back")[2][2], cube.get("bottom")[2][2] );

                front[0][2] = (char)lst1.get(0); top[0][2] = (char)lst1.get(1); back[0][2] = (char)lst1.get(2); bottom[0][2] = (char)lst1.get(3);
                front[1][2] = (char)lst2.get(0); top[1][2] = (char)lst2.get(1); back[1][2] = (char)lst2.get(2); bottom[1][2] = (char)lst2.get(3);
                front[2][2] = (char)lst3.get(0); top[2][2] = (char)lst3.get(1); back[2][2] = (char)lst3.get(2); bottom[2][2] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("front")[0][2], cube.get("bottom")[0][2], cube.get("back")[0][2], cube.get("top")[0][2] );
                ArrayList lst2 = swapFourElements( cube.get("front")[1][2], cube.get("bottom")[1][2], cube.get("back")[1][2], cube.get("top")[1][2] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][2], cube.get("bottom")[2][2], cube.get("back")[2][2], cube.get("top")[2][2] );

                front[0][2] = (char)lst1.get(0); bottom[0][2] = (char)lst1.get(1); back[0][2] = (char)lst1.get(2); top[0][2] = (char)lst1.get(3);
                front[1][2] = (char)lst2.get(0); bottom[1][2] = (char)lst2.get(1); back[1][2] = (char)lst2.get(2); top[1][2] = (char)lst2.get(3);
                front[2][2] = (char)lst3.get(0); bottom[2][2] = (char)lst3.get(1); back[2][2] = (char)lst3.get(2); top[2][2] = (char)lst3.get(3);
            }
            cube.put("front",front);
            cube.put("bottom", bottom);
            cube.put("back",back);
            cube.put("top", top);
        }

        else if (layer.equals( "left" )){
            char[][] front = cube.get("front");
            char[][] bottom = cube.get("bottom");
            char[][] back = cube.get("back");
            char[][] top = cube.get("top");
            if (direction=='c'){
                ArrayList lst1 = swapFourElements( cube.get("front")[0][0], cube.get("top")[0][0], cube.get("back")[0][0], cube.get("bottom")[0][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[1][0], cube.get("top")[1][0], cube.get("back")[1][0], cube.get("bottom")[1][0] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][0], cube.get("top")[2][0], cube.get("back")[2][0], cube.get("bottom")[2][0] );

                back[0][0] = (char)lst1.get(0); bottom[0][0] = (char)lst1.get(1); front[0][0] = (char)lst1.get(2); top[0][0] = (char)lst1.get(3);
                back[1][0] = (char)lst2.get(0); bottom[1][0] = (char)lst2.get(1); front[1][0] = (char)lst2.get(2); top[1][0] = (char)lst2.get(3);
                back[2][0] = (char)lst3.get(0); bottom[2][0] = (char)lst3.get(1); front[2][0] = (char)lst3.get(2); top[2][0] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("front")[0][0], cube.get("bottom")[0][0], cube.get("back")[0][0], cube.get("top")[0][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[1][0], cube.get("bottom")[1][0], cube.get("back")[1][0], cube.get("top")[1][0] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][0], cube.get("bottom")[2][0], cube.get("back")[2][0], cube.get("top")[2][0] );

                back[0][0] = (char)lst1.get(0); top[0][0] = (char)lst1.get(1); front[0][0] = (char)lst1.get(2); bottom[0][0] = (char)lst1.get(3);
                back[1][0] = (char)lst2.get(0); top[1][0] = (char)lst2.get(1); front[1][0] = (char)lst2.get(2); bottom[1][0] = (char)lst2.get(3);
                back[2][0] = (char)lst3.get(0); top[2][0] = (char)lst3.get(1); front[2][0] = (char)lst3.get(2); bottom[2][0] = (char)lst3.get(3);
            }
            cube.put("front",front);
            cube.put("bottom", bottom);
            cube.put("back",back);
            cube.put("top", top);
        }

        else if (layer.equals( "top" )){
            char[][] front = cube.get("front");
            char[][] right = cube.get("right");
            char[][] back = cube.get("back");
            char[][] left = cube.get("left");
            if (direction=='c'){
                ArrayList lst1 = swapFourElements( cube.get("front")[0][0], cube.get("left")[0][0], cube.get("back")[2][2], cube.get("right")[0][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[0][1], cube.get("left")[0][1], cube.get("back")[2][1], cube.get("right")[0][1] );
                ArrayList lst3 = swapFourElements( cube.get("front")[0][2], cube.get("left")[0][2], cube.get("back")[2][0], cube.get("right")[0][2] );

                front[0][0] = (char)lst1.get(0); left[0][0] = (char)lst1.get(1); back[2][2] = (char)lst1.get(2); right[0][0] = (char)lst1.get(3);
                front[0][1] = (char)lst2.get(0); left[0][1] = (char)lst2.get(1); back[2][1] = (char)lst2.get(2); right[0][1] = (char)lst2.get(3);
                front[0][2] = (char)lst3.get(0); left[0][2] = (char)lst3.get(1); back[2][0] = (char)lst3.get(2); right[0][2] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("front")[0][0], cube.get("right")[0][0], cube.get("back")[2][2], cube.get("left")[0][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[0][1], cube.get("right")[0][1], cube.get("back")[2][1], cube.get("left")[0][1] );
                ArrayList lst3 = swapFourElements( cube.get("front")[0][2], cube.get("right")[0][2], cube.get("back")[2][0], cube.get("left")[0][2] );

                front[0][0] = (char)lst1.get(0); right[0][0] = (char)lst1.get(1); back[2][2] = (char)lst1.get(2); left[0][0] = (char)lst1.get(3);
                front[0][1] = (char)lst2.get(0); right[0][1] = (char)lst2.get(1); back[2][1] = (char)lst2.get(2); left[0][1] = (char)lst2.get(3);
                front[0][2] = (char)lst3.get(0); right[0][2] = (char)lst3.get(1); back[2][0] = (char)lst3.get(2); left[0][2] = (char)lst3.get(3);
            }
            cube.put("front",front);
            cube.put("right",right);
            cube.put("back",back);
            cube.put("left",left);
        }
        else if (layer.equals( "bottom" )){
            char[][] front = cube.get("front");
            char[][] right = cube.get("right");
            char[][] back = cube.get("back");
            char[][] left = cube.get("left");
            if (direction=='a'){
                ArrayList lst1 = swapFourElements( cube.get("front")[2][0], cube.get("left")[2][0], cube.get("back")[0][2], cube.get("right")[2][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[2][1], cube.get("left")[2][1], cube.get("back")[0][1], cube.get("right")[2][1] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][2], cube.get("left")[2][2], cube.get("back")[0][0], cube.get("right")[2][2] );

                front[2][0] = (char)lst1.get(0); left[2][0] = (char)lst1.get(1); back[0][2] = (char)lst1.get(2); right[2][0] = (char)lst1.get(3);
                front[2][1] = (char)lst2.get(0); left[2][1] = (char)lst2.get(1); back[0][1] = (char)lst2.get(2); right[2][1] = (char)lst2.get(3);
                front[2][2] = (char)lst3.get(0); left[2][2] = (char)lst3.get(1); back[0][0] = (char)lst3.get(2); right[2][2] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("front")[2][0], cube.get("right")[2][0], cube.get("back")[0][2], cube.get("left")[2][0] );
                ArrayList lst2 = swapFourElements( cube.get("front")[2][1], cube.get("right")[2][1], cube.get("back")[0][1], cube.get("left")[2][1] );
                ArrayList lst3 = swapFourElements( cube.get("front")[2][2], cube.get("right")[2][2], cube.get("back")[0][0], cube.get("left")[2][2] );

                front[2][0] = (char)lst1.get(0); right[2][0] = (char)lst1.get(1); back[0][2] = (char)lst1.get(2); left[2][0] = (char)lst1.get(3);
                front[2][1] = (char)lst2.get(0); right[2][1] = (char)lst2.get(1); back[0][1] = (char)lst2.get(2); left[2][1] = (char)lst2.get(3);
                front[2][2] = (char)lst3.get(0); right[2][2] = (char)lst3.get(1); back[0][0] = (char)lst3.get(2); left[2][2] = (char)lst3.get(3);
            }
            cube.put("front",front);
            cube.put("right",right);
            cube.put("back",back);
            cube.put("left",left);
        }
        else if (layer.equals( "front" )){
            char[][] bottom = cube.get("bottom");
            char[][] right = cube.get("right");
            char[][] top = cube.get("top");
            char[][] left = cube.get("left");
            if (direction=='c'){
                ArrayList lst1 = swapFourElements( cube.get("bottom")[0][0], cube.get("left")[0][2], cube.get("top")[2][2], cube.get("right")[2][0] );
                ArrayList lst2 = swapFourElements( cube.get("bottom")[0][1], cube.get("left")[1][2], cube.get("top")[2][1], cube.get("right")[1][0] );
                ArrayList lst3 = swapFourElements( cube.get("bottom")[0][2], cube.get("left")[2][2], cube.get("top")[2][0], cube.get("right")[0][0] );

                bottom[0][0] = (char)lst1.get(0); left[0][2] = (char)lst1.get(1); top[2][2] = (char)lst1.get(2); right[2][0] = (char)lst1.get(3);
                bottom[0][1] = (char)lst2.get(0); left[1][2] = (char)lst2.get(1); top[2][1] = (char)lst2.get(2); right[1][0] = (char)lst2.get(3);
                bottom[0][2] = (char)lst3.get(0); left[2][2] = (char)lst3.get(1); top[2][0] = (char)lst3.get(2); right[0][0] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("bottom")[0][0], cube.get("right")[2][0], cube.get("top")[2][2], cube.get("left")[0][2] );
                ArrayList lst2 = swapFourElements( cube.get("bottom")[0][1], cube.get("right")[1][0], cube.get("top")[2][1], cube.get("left")[1][2] );
                ArrayList lst3 = swapFourElements( cube.get("bottom")[0][2], cube.get("right")[0][0], cube.get("top")[2][0], cube.get("left")[2][2] );

                bottom[0][0] = (char)lst1.get(0); right[2][0] = (char)lst1.get(1); top[2][2] = (char)lst1.get(2); left[0][2] = (char)lst1.get(3);
                bottom[0][1] = (char)lst2.get(0); right[1][0] = (char)lst2.get(1); top[2][1] = (char)lst2.get(2); left[1][2] = (char)lst2.get(3);
                bottom[0][2] = (char)lst3.get(0); right[0][0] = (char)lst3.get(1); top[2][0] = (char)lst3.get(2); left[2][2] = (char)lst3.get(3);
            }
            cube.put("bottom",bottom);
            cube.put("right",right);
            cube.put("top", top);
            cube.put("left",left);
        }
        else if (layer.equals( "back" )){
            char[][] bottom = cube.get("bottom");
            char[][] right = cube.get("right");
            char[][] top = cube.get("top");
            char[][] left = cube.get("left");
            if (direction=='c'){
                ArrayList lst1 = swapFourElements( cube.get("bottom")[2][0], cube.get("right")[2][2], cube.get("top")[0][2], cube.get("left")[0][0] );
                ArrayList lst2 = swapFourElements( cube.get("bottom")[2][1], cube.get("right")[1][2], cube.get("top")[0][1], cube.get("left")[1][0] );
                ArrayList lst3 = swapFourElements( cube.get("bottom")[2][2], cube.get("right")[0][2], cube.get("top")[0][0], cube.get("left")[2][0] );

                bottom[2][0] = (char)lst1.get(0); right[2][2] = (char)lst1.get(1); top[0][2] = (char)lst1.get(2); left[0][0] = (char)lst1.get(3);
                bottom[2][1] = (char)lst2.get(0); right[1][2] = (char)lst2.get(1); top[0][1] = (char)lst2.get(2); left[1][0] = (char)lst2.get(3);
                bottom[2][2] = (char)lst3.get(0); right[0][2] = (char)lst3.get(1); top[0][0] = (char)lst3.get(2); left[2][0] = (char)lst3.get(3);
            }
            else{
                ArrayList lst1 = swapFourElements( cube.get("bottom")[2][0], cube.get("left")[0][0], cube.get("top")[0][2], cube.get("right")[2][2] );
                ArrayList lst2 = swapFourElements( cube.get("bottom")[2][1], cube.get("left")[1][0], cube.get("top")[0][1], cube.get("right")[1][2] );
                ArrayList lst3 = swapFourElements( cube.get("bottom")[2][2], cube.get("left")[2][0], cube.get("top")[0][0], cube.get("right")[0][2] );

                bottom[2][0] = (char)lst1.get(0); left[0][0] = (char)lst1.get(1); top[0][2] = (char)lst1.get(2); right[2][2] = (char)lst1.get(3);
                bottom[2][1] = (char)lst2.get(0); left[1][0] = (char)lst2.get(1); top[0][1] = (char)lst2.get(2); right[1][2] = (char)lst2.get(3);
                bottom[2][2] = (char)lst3.get(0); left[2][0] = (char)lst3.get(1); top[0][0] = (char)lst3.get(2); right[0][2] = (char)lst3.get(3);
            }
            cube.put("bottom",bottom);
            cube.put("right",right);
            cube.put("top", top);
            cube.put("left",left);
        }
    }

    public Collection<Configuration> getSuccessors(){

        ArrayList<Configuration> successors = new ArrayList<>();
        //do all possible operations with methods defined in cubeConfig
        for (String face:this.faces){
            for (char d:this.directions){
                CubeConfig newCube = new CubeConfig(this);
                newCube.move(face, d);
                newCube.moves.add("Move "+face+" "+Character.toString(d)+".");
                successors.add(newCube);
            }
        }
        return successors;
    }

    /**
     * invalid if the last 3 moves are same or nullify each other
     */
    public boolean isValid(){
        if (this.depth == 8){//to prevent the program from trying more than 7 moves
            return false;
        }
        int L = moves.size();
        //last 3 moves same (could do the same with just one move in the opposite direction)
        if (L>=3) {
            if (moves.get(L - 1).equals(moves.get(L - 2)) && moves.get(L - 1).equals(moves.get(L - 3))) {
                return false;
            }
        }
        //moves that cancel each other's effects
        if (L>=2) {
            if (Arrays.asList(moves.get(L - 1).split(" ")).get(1).equals(Arrays.asList(moves.get(L - 2).split(" ")).get(1))) {
                if (Arrays.asList(moves.get(L - 1).split(" ")).get(2).substring(0,1).equals(oppMoves.get((Arrays.asList(moves.get(L - 2).split(" ")).get(2).substring(0,1))))) {
                    return false;
                }
            }
        }
        //System.out.println(moves.get(moves.size()-1));
        return true;
    }

    /**
     * Check if cube is solves
     * for each face compare every other to the center
     */
    public boolean isGoal(){
        for (String face:cube.keySet()){
            char K = cube.get(face)[1][1];
            for (int i=0;i<3;i++){
                for (int j=0; j<3;j++){
                    if (cube.get(face)[i][j] != K){
                        return false;
                    }
                }
            }
        }
        System.out.println("Steps to solution: ");
        for (String s:moves){
            System.out.println("    "+s);
        }
        System.out.println();
        return true;
    }

    /**
     * return string representation of one face of the cube
     * @param face
     * @return
     */
    public String faceString(String face){
        char[][] Face = cube.get(face);
        String st = " -------\n";
        for (int i=0;i<dim;i++) {
            st += "| ";
            for (int j=0;j<dim;j++){
                st += cube.get(face)[i][j]+" ";
            }
            st+="|\n";
        }
        st += " -------\n";
        return st;
    }

    @Override
    public String toString(){
        String st = "CUBE:\nDepth: "+depth+"\n";
        st += ("top: \n"+faceString("top"));
        st += ("front: \n"+faceString("front"));
        st += ("left: \n"+faceString("left"));
        st += ("right: \n"+faceString("right"));
        st += ("bottom: \n"+faceString("bottom"));
        st += ("back: \n"+faceString("back"));
        return st;
    }

    public ArrayList<String> getMoves(){
        return  this.moves;
    }

    public void display(){
        System.out.println(this);
    }

    public int getdepth(){ return depth; }


}
