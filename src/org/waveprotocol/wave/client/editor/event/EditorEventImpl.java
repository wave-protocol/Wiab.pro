/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.waveprotocol.wave.client.editor.event;

import com.google.gwt.event.dom.client.KeyCodes;
import org.waveprotocol.wave.client.common.util.SignalEventImpl;
import org.waveprotocol.wave.client.editor.content.ContentPoint;

/**
 * @author danilatos@google.com (Daniel Danilatos)
 */
public class EditorEventImpl extends SignalEventImpl implements EditorEvent {

  public static SignalEventFactory<EditorEventImpl> FACTORY =
    new SignalEventFactory<EditorEventImpl>() {
      @Override public EditorEventImpl create() {
        return new EditorEventImpl();
      }
    };

  private EditorEventImpl() {
  }

  private boolean shouldAllowDefault = false;
  private ContentPoint caret;

  @Override
  public void allowBrowserDefault() {
    shouldAllowDefault = true;
  }


  @Override
  public ContentPoint getCaret() {
    return caret;
  }

  @Override
  public void setCaret(ContentPoint caret) {
    this.caret = caret;
  }

  @Override
  public boolean shouldAllowBrowserDefault() {
    return shouldAllowDefault;
  }

  @Override
  public boolean shouldTrackCursor() {
    if (isMouseButtonEvent()) {
      return true;
    }
    if (isKeyEvent()) {
      KeySignalType keySignalType = getKeySignalType();
      // The cursor location should move if the user either has modified the
      // content (typed or delete), or move the cursor deliberately.  However, page up/down
      // doesn't actually move the cursor, so we don't want to move the view port
      int keyCode = getKeyCode();
      return keySignalType == KeySignalType.INPUT ||
            keySignalType == KeySignalType.DELETE ||
            keySignalType == KeySignalType.NAVIGATION && (
               keyCode != KeyCodes.KEY_PAGEDOWN &&
               keyCode != KeyCodes.KEY_PAGEUP);
    }
    return false;    
  }
}
