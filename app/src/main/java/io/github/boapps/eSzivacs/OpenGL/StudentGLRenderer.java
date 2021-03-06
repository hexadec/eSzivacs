package io.github.boapps.eSzivacs.OpenGL;
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.github.boapps.eSzivacs.Datas.Subject;

import static io.github.boapps.eSzivacs.Activities.MainPage.subjects;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class StudentGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "StudentGLRenderer";
    public static float mAngle;
    public static int selectedLesson = 0;
    public static int selectedLessonPrev = 0;
    private static ArrayList<Triangle> triangles = new ArrayList<>();
    private static ArrayList<Triangle> selectionTriangles = new ArrayList<>();
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private ArrayList<Subject> newsubjects = subjects;

    /**
     * Utility method for compiling a OpenGL shader.
     * <p>
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */


    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p>
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     * <p>
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        FirebaseCrash.log("onSurfaceCreated");

        // Set the background frame color
        GLES20.glClearColor(0.176f, 0.190f, 0.197f, 1.0f);//rgb(176,190,197)
        FirebaseCrash.log("GLES20.glClearColor");

//        GLES20.glGetIntegerv(GL_VIEWPORT, );
//
//        System.out.println(mGLView.getWidth());
//        System.out.println(mGLView.getHeight());
//        System.out.println(mGLView.getX());
//        System.out.println(mGLView.getPivotX());
//        System.out.println(mGLView.getPivotY());
//        System.out.println( (float) mGLView.getWidth() / (float) mGLView.getHeight());

//        int jegyek[] = {5, 3, 4, 4, 2, 3, 1, 5, 2, 2, 1, 4, 1, 4, 4, 4, 5, 1};//, 4, 2, 3, 1, 5

//        float startpoint = (float) mGLView.getWidth() / (float) mGLView.getHeight();


        int hossz = 1;
        int oldal = newsubjects.size();
        FirebaseCrash.log("newsubjects.size(): " + newsubjects.size());

        float angle = (float) Math.toRadians((float) 360 / oldal);
        for (int n = 0; n < oldal; n++) {
            float trianglecs[] = {
                    0, 0, 0,
                    0.8f * (float) Math.cos(angle * n + Math.toRadians(270)), 0.8f * (float) Math.sin(angle * n + Math.toRadians(270)), 0,
                    0.8f * (float) Math.cos(angle * (n + 1) + Math.toRadians(270)), 0.8f * (float) Math.sin(angle * (n + 1) + Math.toRadians(270)), 0
            };
            for (float i : trianglecs)
                System.out.println(i);
            Triangle triangle = new Triangle();
            triangle.setCoords(trianglecs);
            triangle.initialise();
            triangles.add(triangle);


//            float trianglecs2[] = {
//                    // in counterclockwise order:
//                    -2 * startpoint * (float) (n + 1) / (float) (jegyek.length - 1) + startpoint, (float) jegyek[n + 1] / 2.5f - 1, 0.0f,   // top
//                    -2 * startpoint * (float) (n + 1) / (float) (jegyek.length - 1) + startpoint, 0 * 0.1f - 1, 0.0f,   // bottom left
//                    -2 * startpoint * (float) n / (float) (jegyek.length - 1) + startpoint, 0 * 0.1f - 1, 0.0f,    // bottom right
//            };
//
//            Triangle triangle2 = new Triangle();
//            triangle2.setCoords(trianglecs2);
//            triangle2.initialise();
//            triangles.add(triangle2);

        }
        for (int n = 0; n < oldal; n++) {
            float trianglecs[] = {
                    0, 0, 0,
                    0.8f * (float) newsubjects.get(n).getValue() * (float) Math.cos(angle * n + Math.toRadians(270)) / 5, 0.8f * (float) newsubjects.get(n).getValue() * (float) Math.sin(angle * n + Math.toRadians(270)) / 5, 0,
                    0.8f * (float) newsubjects.get((n + 1) % oldal).getValue() * (float) Math.cos(angle * (n + 1) + Math.toRadians(270)) / 5, 0.8f * (float) newsubjects.get((n + 1) % oldal).getValue() * (float) Math.sin(angle * (n + 1) + Math.toRadians(270)) / 5, 0
            };
            System.out.println("oldal:" + (n + 1) % oldal);
            for (float i : trianglecs)
                System.out.println(i);
            Triangle triangle = new Triangle();
            triangle.setCoords(trianglecs);
            triangle.initialise();
            float color[] = {0.62f, 0.39f, 0.35f, 0.181f};//rgba(62,39,35 ,1)
            triangle.setColor(color);
            triangles.add(triangle);


//            float trianglecs2[] = {
//                    // in counterclockwise order:
//                    -2 * startpoint * (float) (n + 1) / (float) (jegyek.length - 1) + startpoint, (float) jegyek[n + 1] / 2.5f - 1, 0.0f,   // top
//                    -2 * startpoint * (float) (n + 1) / (float) (jegyek.length - 1) + startpoint, 0 * 0.1f - 1, 0.0f,   // bottom left
//                    -2 * startpoint * (float) n / (float) (jegyek.length - 1) + startpoint, 0 * 0.1f - 1, 0.0f,    // bottom right
//            };
//
//            Triangle triangle2 = new Triangle();
//            triangle2.setCoords(trianglecs2);
//            triangle2.initialise();
//            triangles.add(triangle2);

        }

    }

    public void selectLesson(int n) {
        FirebaseCrash.log("selectLesson");
        selectionTriangles.clear();

        int hossz = 1;
        int oldal = subjects.size();
        float angle = (float) Math.toRadians((float) 360 / oldal);
//        0, 0, 0,
//                0.8f * (float) ((newsubjects.get(s).getValue() + newsubjects.get(s+1).getValue())/2) * (float) Math.cos(angle * s + Math.toRadians(270) + angle/2)/5, /*    */0.8f * (float) ((newsubjects.get(s).getValue() + newsubjects.get(s+1).getValue())/2) * (float) Math.sin(angle * s + Math.toRadians(270) + angle/2)/5, 0,
//                0.8f * (float) newsubjects.get((s)%oldal).getValue() * (float) Math.cos(angle * (s) + Math.toRadians(270))/5, 0.8f * (float) newsubjects.get((s)%oldal).getValue() * (float) Math.sin(angle * (s) + Math.toRadians(270))/5, 0

        float trianglecs[] = {
                0, 0, 0,
                ((0.8f * (float) subjects.get(n).getValue() * (float) Math.cos(angle * n + Math.toRadians(270)) / 5) + (0.8f * (float) subjects.get((n + 1) % oldal).getValue() * (float) Math.cos(angle * ((n + 1) % oldal) + Math.toRadians(270)) / 5)) / 2, ((0.8f * (float) subjects.get(n).getValue() * (float) Math.sin(angle * n + Math.toRadians(270)) / 5) + (0.8f * (float) subjects.get((n + 1) % oldal).getValue() * (float) Math.sin(angle * ((n + 1) % oldal) + Math.toRadians(270)) / 5)) / 2, 0,
                0.8f * (float) subjects.get((n) % oldal).getValue() * (float) Math.cos(angle * (n) + Math.toRadians(270)) / 5, 0.8f * (float) subjects.get((n) % oldal).getValue() * (float) Math.sin(angle * (n) + Math.toRadians(270)) / 5, 0
        };
        Triangle triangle = new Triangle();
        triangle.setCoords(trianglecs);
        triangle.initialise();
        float color[] = {0.20f, 0.32f, 0.31f, 0.21f};//rgba(62,39,35 ,1)
        triangle.setColor(color);
        selectionTriangles.add(triangle);

        float trianglecs2[] = {
                0, 0, 0,
                ((0.8f * (float) subjects.get(n).getValue() * (float) Math.cos(angle * n + Math.toRadians(270)) / 5) + (0.8f * (float) subjects.get((n + oldal - 1) % oldal).getValue() * (float) Math.cos(angle * ((n + oldal - 1) % oldal) + Math.toRadians(270)) / 5)) / 2, ((0.8f * (float) subjects.get(n).getValue() * (float) Math.sin(angle * n + Math.toRadians(270)) / 5) + (0.8f * (float) subjects.get((n + oldal - 1) % oldal).getValue() * (float) Math.sin(angle * ((n + oldal - 1) % oldal) + Math.toRadians(270)) / 5)) / 2, 0,
                0.8f * (float) subjects.get((n) % oldal).getValue() * (float) Math.cos(angle * (n) + Math.toRadians(270)) / 5, 0.8f * (float) subjects.get((n) % oldal).getValue() * (float) Math.sin(angle * (n) + Math.toRadians(270)) / 5, 0
        };
        Triangle triangle2 = new Triangle();
        triangle2.setCoords(trianglecs2);
        triangle2.initialise();
        triangle2.setColor(color);
        selectionTriangles.add(triangle2);

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];

        if (selectedLessonPrev != selectedLesson) {
            selectedLessonPrev = selectedLesson;
            selectLesson(selectedLesson);
        }
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw square
//        mSquare.draw(mMVPMatrix);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
//        mTriangle.draw(scratch);

        for (int n = 0; n < triangles.size(); n++) {
            triangles.get(n).draw(scratch);
        }

        for (int n = 0; n < selectionTriangles.size(); n++) {
            selectionTriangles.get(n).draw(scratch);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
//        System.out.println("width is " + width);
//        System.out.println("height is " + height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = (angle + 180) % 360;
    }

}